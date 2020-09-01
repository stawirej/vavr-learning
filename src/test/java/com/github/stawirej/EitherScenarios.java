package com.github.stawirej;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class EitherScenarios {

    Either<Error, Value> compute() {

        //        int quotient = 1 / 0;
        //        Either.right(new Value());
        return Either.left(new Error());
    }

    @Test
    void try_to_left_either() {
        // Given

        // When
        Either<Throwable, Integer> quotient =
                Try.of(() -> 1 / 0)
                        .toEither();

        // Then
        then(quotient.isRight()).isFalse();
        then(quotient.getLeft()).isInstanceOf(Throwable.class);
    }

    @Test
    void try_to_right_either() {
        // Given

        // When
        Either<Throwable, Integer> quotient =
                Try.of(() -> 10 / 2)
                        .toEither();

        // Then
        then(quotient.isRight()).isTrue();
        then(quotient.getOrNull()).isEqualTo(5);
    }

    class Error {

        @Override
        public String toString() {

            return "I am error.";
        }
    }

    class Value {

        @Override
        public String toString() {

            return "I am correct value.";
        }
    }

}
