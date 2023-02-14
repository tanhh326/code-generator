package com.youcon.bp.cg;


/**
 * 创建参数校验接口
 *
 * @param <C> 创建参数对象
 */
public interface CreateValidate<C> extends Validate<C> {

  /**
   * 创建参数验证
   *
   * @param c 创建参数对象
   * @throws IllegalArgumentException 参数异常
   */
  void createValidate(C c) throws IllegalArgumentException;

  @Override
  default void validate(C c) throws IllegalArgumentException {
    createValidate(c);
  }
}
