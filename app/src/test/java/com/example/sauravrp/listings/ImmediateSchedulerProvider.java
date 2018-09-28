package com.example.sauravrp.listings;

import com.example.sauravrp.listings.helpers.interfaces.ISchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements ISchedulerProvider {
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
