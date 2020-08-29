package com.github.stawirej;

import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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
        // Given
        Try.of(() -> {
            int x = 1;
            //            throw new RuntimeException();
            return x;
        })
                .onSuccess(success -> System.out.println("TryScenarios.trySuccess"))
                .onFailure(failure -> System.out.println("TryScenarios.trySuccess"))
                .recover(v -> {
                    System.out.println("Recover" + v);
                    return 1;
                })
                .map(v -> {
                    System.out.println("TryScenarios.trySuccess 2");
                    return v;
                });

        // When

        // Then

    }

    @Test
    void obfuscate_multiple_exceptions() {

        Throwable throwable = catchThrowable(() ->
                Try.of(A::new)
                        .map(A::b)
                        .map(B::c)
                        .map(C::d)
                        .getOrElseThrow(this::obfuscatedException)
        );

        then(throwable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("Obfuscated exception");
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

        @Override
        public String toString() {

            return "a";
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

        @Override
        public String toString() {

            return "d";
        }
    }
}
