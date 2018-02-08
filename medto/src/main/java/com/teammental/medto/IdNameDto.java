package com.teammental.medto;

import java.io.Serializable;

/**
 * Combines {@link IdDto} and {@link NameDto}.
 * @param <IdT> see {@link IdDto}
 */
public interface IdNameDto<IdT extends Serializable>
    extends IdDto<IdT>, NameDto {
}