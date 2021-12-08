package com.thelastpickle.tlpstress

import shaded.com.scylladb.cdc.driver3.common.base.Predicate
import shaded.com.scylladb.cdc.driver3.driver.core.Host

class CoordinatorHostPredicate : Predicate<Host> {
    override fun apply(input: Host?): Boolean {
        if(input == null)
            return false
        return input.tokens == null || input.tokens.size == 0
    }
}

