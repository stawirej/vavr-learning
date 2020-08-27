package com.github.stawirej;

import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

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
    void foo() {
        // Given

        // When

        // Then
    }

    @Test
    void multiple_errors() {

        //        Try<D> tryVar =
        //                Try.of(A::new)
        //                        .map(A::b)
        //                        .onFailure(e -> new IllegalStateException("Shielded exception: " + e.getMessage()))
        //                        .map(B::c)
        //                        .map(C::d);
        //
        //        Match(tryVar).of(
        //                Case($Success($()), value -> value),
        //                Case($Failure($()), value -> {
        //                    System.out.println(value);
        //                    return value;
        //                })
        //        );

        D d = Try.of(A::new)
                .map(A::b)
//                .onFailure()
                .map(B::c)
                //                .onFailure(e -> {
                //                    throw new IllegalStateException("Shielded exception: " + e.getMessage());
                //                })
                .map(C::d)
                .getOrElseThrow(this::handle
                        //                        Match(e).of(
                        //                                Case($(instanceOf(IllegalArgumentException.class)), new IllegalStateException("Shielded exception: " + e.getMessage())),
                        //                                Case($(), new RuntimeException("XXX"))
                        //                        )
                );

        //                .getOrElseThrow(e -> {
        //                    throw new IllegalStateException("Shielded exception: " + e.getMessage());
        //                });

        //        ds.getOrElseThrow(e -> Match(e).of(
        //                Case($(instanceOf(IllegalArgumentException.class)), o -> {
        //                    throw new IllegalStateException("Shielded exception: " + e.getMessage());
        //                }),
        //                Case($(), o -> {
        //                    throw new NumberFormatException();
        //                })));

        //        System.out.println(ds);
    }

    RuntimeException handle(Throwable e) {

        return Match(e).of(
                Case($(instanceOf(IllegalArgumentException.class)), new IllegalStateException("Shielded exception: " + e.getMessage())),
                Case($(), new RuntimeException("XXX"))
        );
    }

    class B {
        C c() {

            //                        return new C();
            throw new IllegalArgumentException("Invalid XYZ!");
            //            throw new ArithmeticException("Invalid DDD!");
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

    class A {
        B b() {

            return new B();
        }

        @Override
        public String toString() {

            return "a";
        }
    }
}
