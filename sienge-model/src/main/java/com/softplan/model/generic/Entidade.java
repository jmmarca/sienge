package com.softplan.model.generic;

import java.io.Serializable;

/**
 * Entidade abstrata que representa uma entidade base genérica.
 *
 * @author Michel
 */
public abstract class Entidade implements Serializable {

    public abstract Integer getId();

    public abstract void setId(Integer id);
}
