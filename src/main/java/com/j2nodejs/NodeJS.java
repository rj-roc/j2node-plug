package com.j2nodejs;


import com.j2node.utils.ResourceLoadUtils;

public class NodeJS {

    public NodeJS(){
        ResourceLoadUtils.loadResourceFileLib("/lib/libj2nodejs-12.so");
    }

    public native void nodeStart(String script);

}
