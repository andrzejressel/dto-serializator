package pl.andrzejressel.sjs.processor

import com.google.testing.compile.CompilationSubject.assertThat
import com.google.testing.compile.Compiler.javac
import com.google.testing.compile.JavaFileObjects
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

class BasicTest {

    @Test
    fun requireSingleConstructor() {

        @Language("java")
        val code =
            """
                import java.util.ArrayList;
                import java.util.List;
                import pl.andrzejressel.sjs.serializator.GenerateSerializator;
                  
                @GenerateSerializator
                final class HelloWorld {
                    public final String s;
                    public final List<String> l;
                        
                    public HelloWorld(String s, List<String> l) {
                        this.s = s;
                        this.l = l;
                    }
                    public HelloWorld(String s) {
                        this.s = s;
                        this.l = new ArrayList<>();
                    }
                }
           """.trimIndent()

        val compilation = javac()
            .withProcessors(BuilderProcessor())
            .compile(
                JavaFileObjects.forSourceString(
                    "HelloWorld",
                    code
                )
            )

        assertThat(compilation)
            .failed()
        assertThat(compilation)
            .hadErrorContaining("class can have only one constructor")
//        assertThat()
    }


    @Test
    fun classWithPublicFields() {

        @Language("java")
        val code =
            """
                package abc.xyz;                         

                import java.util.List;
                import pl.andrzejressel.sjs.serializator.GenerateSerializator;
                  
                @GenerateSerializator
                final class HelloWorld {
                    public final String s;
                    public final List<String> l;

                    public HelloWorld(String s, List<String> l) {
                        this.s = s;
                        this.l = l;
                    }
                }
           """.trimIndent()

        val compilation = javac()
            .withProcessors(BuilderProcessor())
            .compile(
                JavaFileObjects.forSourceString(
                    "abc.xyz.HelloWorld",
                    code
                )
            )

        assertThat(compilation).succeeded()
    }

    @Test
    fun classWithGetters() {

        @Language("java")
        val code =
            """
                package abc.xyz;                         

                import java.util.List;
                import pl.andrzejressel.sjs.serializator.GenerateSerializator;
                  
                @GenerateSerializator
                final class HelloWorld {
                    private final String s;
                    private final List<String> l;

                    public HelloWorld(String s, List<String> l) {
                        this.s = s;
                        this.l = l;
                    }
                    
                    public String getS() {
                        return s;
                    }
                    
                    public List<String> getL() {
                        return l;
                    }
                }
           """.trimIndent()

        val compilation = javac()
            .withProcessors(BuilderProcessor())
            .compile(
                JavaFileObjects.forSourceString(
                    "abc.xyz.HelloWorld",
                    code
                )
            )

        assertThat(compilation).succeeded()
    }


}