package com.tannergooding.minecraftsharp;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public interface nethost_library_unix extends Library
{
    public static final String JNA_LIBRARY_NAME = "nethost";
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(nethost_library_unix.JNA_LIBRARY_NAME);
    public static final nethost_library_unix INSTANCE = (nethost_library_unix)Native.loadLibrary(nethost_library_unix.JNA_LIBRARY_NAME, nethost_library_unix.class);

    public static class get_hostfxr_parameters extends Structure
    {
        public Pointer size;
        public String assembly_path;
        public String dotnet_root;

        public get_hostfxr_parameters()
        {
            super();
        }

        public get_hostfxr_parameters(Pointer peer)
        {
            super(peer);
        }


        public get_hostfxr_parameters(Pointer size, String assembly_path, String dotnet_root)
        {
            super();

            this.size = size;
            this.assembly_path = assembly_path;
            this.dotnet_root = dotnet_root;
        }

        protected List<String> getFieldOrder()
        {
            return Arrays.asList("size", "assembly_path", "dotnet_root");
        }

        public static class ByReference extends get_hostfxr_parameters implements Structure.ByReference
        {
        }

        public static class ByValue extends get_hostfxr_parameters implements Structure.ByValue
        {
        }
    }

    int get_hostfxr_path(ByteBuffer buffer, PointerByReference buffer_size, Pointer parameters);
}
