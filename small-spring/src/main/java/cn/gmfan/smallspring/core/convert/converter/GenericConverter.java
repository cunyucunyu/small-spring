package cn.gmfan.smallspring.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 *通用类型的转换器
 * @author gmfan
 */
public interface GenericConverter {

    /**
     * 返回转换器可以转换的 source 与 target 的所有关系对
     *
     * @return
     */
    Set<ConvertiblePair> getConvertibleTypes();

    /**
     * 将 source 对象转换成 targetType 类型
     * @param source
     * @param sourceType
     * @param targetType
     * @return
     */
    Object convert(Object source, Class sourceType, Class targetType);

    /**
     * 源到目标对象的关系持有者
     */
    final class ConvertiblePair{
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "原类型不能为空");
            Assert.notNull(targetType, "目标类型不不能为空");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) &&
                    this.targetType.equals(other.targetType);
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
