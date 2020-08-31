package com.github.stawirej;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class TryScenarios {

    @Test
    void try_success() throws RuntimeException {

        Try.of(() -> 1 + 1)
                .onSuccess(success -> System.out.println("On success"))
                .onFailure(failure -> System.out.println("On failure"))
                .recover(v -> {
                    System.out.println("Recover");
                    return 0;
                })
                .peek(v -> System.out.println("Peek map"))
                .map(v -> {
                    System.out.println("Map");
                    return v;
                })
                .get();
    }

    @Test
    @SuppressWarnings({ "unchecked", "varargs" })
    void handle_null_with_map_failure() {
        // Given
        A a = new A();

        Throwable throwable =
                catchThrowable(
                        () -> Try.of(a::foo)
                                .map(D::nullCollection)
                                .map(v -> v.stream()
                                        .filter(x -> x.equals("A"))
                                        .collect(Collectors.toList()))
                                .mapFailure(Case($(instanceOf(NullPointerException.class)), new RuntimeException("UUU")))
                                .get()
                );

        then(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("UUU");
    }

    @Test
    void handle_null_with_option() {
        // Given
        A a = new A();

        Throwable throwable =
                catchThrowable(
                        () -> Try.of(a::foo)
                                .map(D::nullCollection)
                                .map(Option::of)
                                .map(v -> v.getOrElseThrow(() -> new RuntimeException("ZZZ")))
                                .get()
                                .stream()
                                .filter(x -> x.equals("A"))
                                .collect(Collectors.toList())
                );

        then(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("ZZZ");
    }

    @Test
    void handle_null_with_option2() {
        // Given
        A a = new A();

        Throwable throwable =
                catchThrowable(
                        () -> Try.of(a::foo)
                                .map(D::nullCollection)
                                .map(Option::of)
                                .get()
                                .onEmpty(() -> {
                                    throw new RuntimeException("GGG");
                                })
                                .get()
                                .stream()
                                .filter(x -> x.equals("A"))
                                .collect(Collectors.toList())
                );

        then(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("GGG");
    }

    @Test
    void handle_null_with_option3() {
        // Given
        A a = new A();

        Throwable throwable =
                catchThrowable(
                        () -> Try.of(a::foo)
                                .map(D::nullCollection)
                                .map(Option::of)
                                .get()
                                .getOrElseThrow(() -> new RuntimeException("LLL"))
                                .stream()
                                .filter(x -> x.equals("A"))
                                .collect(Collectors.toList())
                );

        then(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessageStartingWith("LLL");
    }

    @Test
    void obfuscate_multiple_exceptions() {

        Throwable throwable =
                catchThrowable(
                        () -> Try.of(A::new)
                                .map(A::b)
                                .map(B::c)
                                .map(C::d)
                                .getOrElseThrow(this::obfuscatedException)
                );

        then(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Obfuscated exception");
    }

    @Override
    public String toString() {

        return "a";
    }

    private RuntimeException obfuscatedException(Throwable e) {

        return Match(e).of(
                Case($(instanceOf(IllegalArgumentException.class)), new IllegalStateException("Obfuscated exception: " + e.getMessage())),
                Case($(), new RuntimeException("XXX"))
        );
    }

    class A {
        B b() {

            return new B();
        }

        D foo() {

            return new D();
        }
    }
}

class B {
    C c() {

        throw new IllegalArgumentException("Invalid XYZ!");
    }

    @Override
    public String toString() {

        return "b";
    }
}

class C {

    D d() {

        return new D();
    }

    @Override
    public String toString() {

        return "c";
    }
}

class D {

    List<String> nullCollection() {

        return null;
        //        return Arrays.asList("A", "B");
    }

    @Override
    public String toString() {

        return "d";
    }
}
