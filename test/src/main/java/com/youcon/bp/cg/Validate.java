package com.youcon.bp.cg;

/**
 * 对象验证接口
 *
 * @param <T> 需要验证的对象
 */
public interface Validate<T> {

  /**
   * 验证方法
   *
   * @param t 需要校验的对象
   * @throws IllegalArgumentException 参数异常
   */
  void validate(T t) throws IllegalArgumentException;
}
