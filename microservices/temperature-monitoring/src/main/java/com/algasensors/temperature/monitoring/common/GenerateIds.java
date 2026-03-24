package com.algasensors.temperature.monitoring.common;

import io.hypersistence.tsid.TSID;

public class GenerateIds {

    public static void main(String[] args) {

        TSID tsid1 = TSID.Factory.getTsid();
        TSID tsid2 = TSID.Factory.getTsid();

        System.out.println(tsid1.toLong());
        System.out.println(tsid2.toLong());

        System.out.println();
        System.out.println("--------");
        long id = 817740032086745274L;

        TSID tsid = TSID.from(id);

        System.out.println(tsid);
    }
}
