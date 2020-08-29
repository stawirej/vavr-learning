package com.github.stawirej;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class TupleScenarios {

    @Nested
    class Create {
        @Test
        void tuple() {
            // When
            Tuple2<Integer, String> tuple =
                    Tuple.of(1, "text");

            // Then
            then(tuple._1()).isEqualTo(1);
            then(tuple._2()).isEqualTo("text");
        }
    }

    @Nested
    class Map {
        @Test
        void withFunction() {
            // Given
            Tuple2<Integer, String> tuple =
                    Tuple.of(1, "text");

            Tuple2<Integer, String> expectedTuple =
                    Tuple.of(2, "textmap");

            // When
            Tuple2<Integer, String> mapped =
                    tuple.map(value -> value * 2, text -> text + "map");

            // Then

            then(mapped).isEqualTo(expectedTuple);
        }

        @Test
        void withBiFunction() {
            // Given
            Tuple2<Integer, String> tuple =
                    Tuple.of(1, "text");

            Tuple2<Integer, String> expectedTuple =
                    Tuple.of(2, "textmap");

            // When
            Tuple2<Integer, String> mapped =
                    tuple.map((value, text) -> Tuple.of(value * 2, text + "map"));

            // Then

            then(mapped).isEqualTo(expectedTuple);
        }
    }

    @Nested
    class Transform {

        @Test
        void toNewType() {
            // Given
            Tuple2<Integer, String> tuple =
                    Tuple.of(1, "text");

            // When
            String transformed =
                    tuple
                            .apply((value, text) -> value + "_" + text);

            // Then
            then(transformed).isEqualTo("1_text");
        }
    }
}
