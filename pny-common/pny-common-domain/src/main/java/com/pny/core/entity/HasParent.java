package com.pny.core.entity;

/**
 * 返回parent
 *
 * @author pmyun
 */
@FunctionalInterface
public interface HasParent {
    HasId getParent();
}
