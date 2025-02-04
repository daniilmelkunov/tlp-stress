package com.thelastpickle.tlpstress

import java.util.*
import com.codahale.metrics.Timer
import com.thelastpickle.tlpstress.profiles.IStressRunner
import com.thelastpickle.tlpstress.profiles.Operation
import org.apache.logging.log4j.kotlin.logger
import shaded.com.scylladb.cdc.driver3.common.util.concurrent.FutureCallback
import shaded.com.scylladb.cdc.driver3.driver.core.ResultSet
import java.util.concurrent.Semaphore

/**
 * Callback after a mutation or select
 * This was moved out of the inline ProfileRunner to make populate mode easier
 * as well as reduce clutter
 */
class OperationCallback(val context: StressContext,
                        val semaphore: Semaphore,
                        val startTime: Timer.Context,
                        val runner: IStressRunner,
                        val op: Operation) : FutureCallback<ResultSet> {

    companion object {
        val log = logger()
    }

    override fun onFailure(t: Throwable?) {
        semaphore.release()
        context.metrics.errors.mark()
        startTime.stop()

        log.error { t }

    }

    override fun onSuccess(result: ResultSet?) {
        semaphore.release()
        startTime.stop()

        // we only do the callback for mutations
        // might extend this to select, but I can't see a reason for it now
        if(op is Operation.Mutation) {
            runner.onSuccess(op, result)
        }

    }
}