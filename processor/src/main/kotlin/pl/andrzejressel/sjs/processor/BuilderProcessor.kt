package pl.andrzejressel.sjs.processor

import com.google.auto.service.AutoService
import pl.andrzejressel.sjs.serializator.IntegerSerializator
import pl.andrzejressel.sjs.serializator.ListSerializator
import pl.andrzejressel.sjs.serializator.MapSerializator
import pl.andrzejressel.sjs.serializator.StringSerializator
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind.*
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.WildcardType
import javax.tools.Diagnostic.Kind.ERROR

@SupportedAnnotationTypes("pl.andrzejressel.sjs.serializator.GenerateSerializator")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor::class)
class BuilderProcessor : AbstractProcessor() {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {

        for (annotation in annotations) {
            for (clz in roundEnv.getElementsAnnotatedWith(annotation)) {

                val constructor = clz.enclosedElements
                    .filterIsInstance<ExecutableElement>()
                    .singleOrNull { it.kind == CONSTRUCTOR }

                if (constructor == null) {
                    printError("class can have only one constructor", clz)
                    continue
                }

                val parameters = constructor.parameters

                val namesWithType = parameters
                    .map { it.simpleName to it.asType() as DeclaredType }

                val serializator = namesWithType.map { it.first to createSerializator(it.second) }

                val qualifiedName = clz.asType().toString().split('.')

                val serializatorClassName = qualifiedName.last() + "Serializator"
                val pcg = if (qualifiedName.size == 1) {
                    ""
                } else {
                    "package ${qualifiedName.dropLast(1).joinToString(separator = ".")};"
                }

                fun fieldAccessor(name: String): String {
                    val getterName = "get${name.replaceFirstChar { it.uppercaseChar() }}()"
                    val field = clz.enclosedElements.firstOrNull { it.kind == FIELD && it.toString() == name }
                    val getter = clz.enclosedElements.firstOrNull { it.kind == METHOD && it.toString() == getterName }
                    return if (getter != null) {
                        getterName
                    } else {
                        field.toString()
                    }
                    //TODO: Add error
                }

                val alphabet = ('a'..'z').take(serializator.size)

                val serializators = "new Tuple${serializator.size}<>(${serializator.joinToString { it.second }})"
                val deconstruct =
                    "obj -> new Tuple${serializator.size}<>(${serializator.joinToString { "obj.${fieldAccessor(it.first.toString())}" }})"
                val construct = "tuple -> new ${clz.asType()}(${alphabet.joinToString { "tuple.${it}" }})"

                val clzContent = """
                |$pcg
                |
                |import pl.andrzejressel.sjs.serializator.*;
                |
                |public class $serializatorClassName {
                |   public static final Serializator<${clz.asType()}> INSTANCE = new Map${serializator.size}Serializator<>(
                |       $serializators,
                |       $deconstruct,
                |       $construct
                |   );
                |}
                |
            """.trimMargin()

                val file = processingEnv.filer.createSourceFile(clz.asType().toString() + "Serializator")

                file.openWriter().use { it.write(clzContent) }
            }
        }

        return true
    }

    private fun createSerializator(clz: DeclaredType): String {

        val genericParameters = clz.typeArguments

        val serializator = getBuildInSerializators(clz) ?: getCustomSerializator(clz)

        return if (genericParameters.isEmpty()) {
            "$serializator.INSTANCE"
        } else {
            val genericParametersSerializator = genericParameters.map {
                val dt = when(it) {
                    is DeclaredType -> it
                    //TODO: Test List<List<List<List<....>>>>
                    is WildcardType -> it.extendsBound as DeclaredType
                    else -> TODO("Unhandled TypeMirror type: $it")
                }
                createSerializator(dt)
            }
            "new ${serializator}<>(${genericParametersSerializator.joinToString()})"
        }
    }

    private fun getCustomSerializator(clz: DeclaredType): String {
        return "${clz.asElement()}Serializator"
    }

    private fun getBuildInSerializators(clz: DeclaredType): String? {
        return when (clz.asElement().toString()) {
            "java.lang.Integer" -> IntegerSerializator::class.simpleName
            "java.lang.String" -> StringSerializator::class.simpleName
            "java.util.List" -> ListSerializator::class.simpleName
            "java.util.Map" -> MapSerializator::class.simpleName
            else -> null
        }
    }

    private fun printError(message: String, element: Element) {
        processingEnv.messager.printMessage(ERROR, message, element)
    }
}