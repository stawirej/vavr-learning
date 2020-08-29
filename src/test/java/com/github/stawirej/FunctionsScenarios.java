package com.github.stawirej;

import io.vavr.Function1;
import io.vavr.Function3;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FunctionsScenarios {

    @Nested
    class Create {

        @Test
        void function3() {
            // Given
            Function3<Integer, Integer, Integer, Integer> sum =
                    (a, b, c) -> a + b + c;

            // When
            Integer sum1 =
                    sum.apply(1).apply(2).apply(3);

            Integer sum2 =
                    sum.apply(1, 3, 5);

            // Then
            then(sum1).isEqualTo(6);
            then(sum2).isEqualTo(9);
        }

        @Test
        void fromReferenceMethod() {
            // Given
            Function3<Integer, Integer, Integer, Integer> sum =
                    Function3.of(this::sum);

            // When
            int result =
                    sum.apply(1, 3, 5);

            // Then
            then(result).isEqualTo(9);
        }

        private int sum(
                Integer a,
                Integer b,
                Integer c) {

            return a + b + c;
        }
    }

    @Nested
    class Composition {

        @Test
        void compose() {
            // Given
            Function1<Integer, Integer> plusOne =
                    a -> a + 1;

            Function1<Integer, Integer> multiplyByTwo =
                    a -> a * 2;

            // When
            Function1<Integer, Integer> add1AndMultiplyBy2 =
                    multiplyByTwo.compose(plusOne);

            // Then
            then(add1AndMultiplyBy2.apply(2)).isEqualTo(6);
        }
    }

    @Nested
    class Lifting {

        @Test
        void lift() {
            // Given

            // When

            // Then
        }

    }

}
