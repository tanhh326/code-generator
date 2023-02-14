package com.github.huifer.gn.core;


/**
 * 更新参数校验接口
 *
 * @param <U> 更新参数对象
 */
public interface UpdateValidate<U> extends Validate<U> {

  /**
   * 更新参数验证
   *
   * @param u 更新参数对象
   * @throws IllegalArgumentException 参数异常
   */
  void updateValidate(U u) throws IllegalArgumentException;

  @Override
  default void validate(U u) throws IllegalArgumentException {
    updateValidate(u);
  }
}
