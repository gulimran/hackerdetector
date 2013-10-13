package com.imrang.detector.convert;

public interface Converter<T, U> {
    T convert(U input);
}
