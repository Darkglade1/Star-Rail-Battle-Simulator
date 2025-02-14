package art.ameliah.hsr.metrics;

public abstract class CounterMetric<T> extends AbstractMetric {

    protected T value;
    protected T lastValue;

    private CounterMetric(String key, String desc, T startValue) {
        super(key, desc);
        this.value = startValue;
    }

    public static CounterMetric<Integer> newIntegerCounter(String key) {
        return newIntegerCounter(key, "", 0);
    }

    public static CounterMetric<Integer> newIntegerCounter(String key, String desc) {
        return newIntegerCounter(key, desc, 0);
    }

    public static CounterMetric<Integer> newIntegerCounter(String key, String desc, Integer value) {
        return new IntCounterMetric(key, desc, value);
    }

    public static CounterMetric<Float> newFloatCounter(String key) {
        return newFloatCounter(key, "", 0f);
    }

    public static CounterMetric<Float> newFloatCounter(String key, String desc) {
        return newFloatCounter(key, desc, 0f);
    }

    public static CounterMetric<Float> newFloatCounter(String key, String desc, Float value) {
        return new FloatCounterMetric(key, desc, value);
    }

    public static CounterMetric<Double> newDoubleCounter(String key) {
        return newDoubleCounter(key, "", 0.0);
    }

    public static CounterMetric<Double> newDoubleCounter(String key, String desc) {
        return newDoubleCounter(key, desc, 0.0);
    }

    public static CounterMetric<Double> newDoubleCounter(String key, String desc, Double value) {
        return new DoubleCounterMetric(key, desc, value);
    }

    public static CounterMetric<Long> newLongCounter(String key) {
        return newLongCounter(key, "", 0L);
    }

    public static CounterMetric<Long> newLongCounter(String key, String desc) {
        return newLongCounter(key, desc, 0L);
    }

    public static CounterMetric<Long> newLongCounter(String key, String desc, Long value) {
        return new LongCounterMetric(key, desc, value);
    }

    public T get() {
        return this.value;
    }

    public T last() {
        return this.lastValue;
    }

    public void set(T value) {
        this.lastValue = this.value;
        this.value = value;
    }

    abstract public void increment();
    abstract public void decrement();
    abstract public void increase(T value);
    abstract public T increase(T value, T max);
    abstract public void decrease(T value);
    abstract public T decrease(T value, T min);


    private static class IntCounterMetric extends CounterMetric<Integer> {

        private IntCounterMetric(String key, String desc, Integer startValue) {
            super(key, desc, startValue);
        }

        @Override
        public void increment() {
            this.lastValue = this.value;
            this.value++;
        }

        @Override
        public void decrement() {
            this.lastValue = this.value;
            this.value--;
        }

        @Override
        public void increase(Integer value) {
            this.lastValue = this.value;
            this.value += value;
        }

        @Override
        public Integer increase(Integer value, Integer max) {
            this.lastValue = this.value;
            this.value = Math.min(this.value + value, max);
            return Math.abs(this.lastValue + value - this.value);
        }

        @Override
        public void decrease(Integer value) {
            this.lastValue = this.value;
            this.value -= value;
        }

        @Override
        public Integer decrease(Integer value, Integer min) {
            this.lastValue = this.value;
            this.value = Math.max(this.value - value, min);
            return this.value;
        }

        @Override
        public String representation() {
            return String.format("%s: %,d", this.getDescription(), this.value);
        }
    }

    private static class FloatCounterMetric extends CounterMetric<Float> {

        private FloatCounterMetric(String key, String desc, Float startValue) {
            super(key, desc, startValue);
        }

        @Override
        public void increment() {
            this.lastValue = this.value;
            this.value++;
        }

        @Override
        public void decrement() {
            this.lastValue = this.value;
            this.value--;
        }

        @Override
        public void increase(Float value) {
            this.lastValue = this.value;
            this.value += value;
        }

        @Override
        public Float increase(Float value, Float max) {
            this.lastValue = this.value;
            this.value = Math.min(this.value + value, max);
            return Math.abs(this.lastValue + value - this.value);
        }

        @Override
        public void decrease(Float value) {
            this.lastValue = this.value;
            this.value -= value;
        }

        @Override
        public Float decrease(Float value, Float min) {
            this.lastValue = this.value;
            this.value = Math.max(this.value - value, min);
            return Math.abs(this.lastValue - (value + this.value));
        }

        @Override
        public String representation() {
            return String.format("%s: %,.2f", this.getDescription(), this.value);
        }
    }

    private static class DoubleCounterMetric extends CounterMetric<Double> {

        private DoubleCounterMetric(String key, String desc, Double startValue) {
            super(key, desc, startValue);
        }

        @Override
        public void increment() {
            this.lastValue = this.value;
            this.value++;
        }

        @Override
        public void decrement() {
            this.lastValue = this.value;
            this.value--;
        }

        @Override
        public void increase(Double value) {
            this.lastValue = this.value;
            this.value += value;
        }

        @Override
        public Double increase(Double value, Double max) {
            this.lastValue = this.value;
            this.value = Math.min(this.value + value, max);
            return Math.abs(this.lastValue + value - this.value);
        }

        @Override
        public void decrease(Double value) {
            this.lastValue = this.value;
            this.value -= value;
        }

        @Override
        public Double decrease(Double value, Double min) {
            this.lastValue = this.value;
            this.value = Math.max(this.value - value, min);
            return Math.abs(this.lastValue - (value + this.value));
        }

        @Override
        public String representation() {
            return String.format("%s: %.2b", this.getDescription(), this.value);
        }
    }

    private static class LongCounterMetric extends CounterMetric<Long> {

        private LongCounterMetric(String key, String desc, Long startValue) {
            super(key, desc, startValue);
        }

        @Override
        public void increment() {
            this.lastValue = this.value;
            this.value++;
        }

        @Override
        public void decrement() {
            this.lastValue = this.value;
            this.value--;
        }

        @Override
        public void increase(Long value) {
            this.lastValue = this.value;
            this.value += value;
        }

        @Override
        public Long increase(Long value, Long max) {
            this.lastValue = this.value;
            this.value = Math.min(this.value + value, max);
            return Math.abs(this.lastValue + value - this.value);
        }

        @Override
        public void decrease(Long value) {
            this.lastValue = this.value;
            this.value -= value;
        }

        @Override
        public Long decrease(Long value, Long min) {
            this.lastValue = this.value;
            this.value = Math.max(this.value - value, min);
            return Math.abs(this.lastValue - (value + this.value));
        }

        @Override
        public String representation() {
            return String.format("%s: %,d", this.getDescription(), this.value);
        }
    }




}
