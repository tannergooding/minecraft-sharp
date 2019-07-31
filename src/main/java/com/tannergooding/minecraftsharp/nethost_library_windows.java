package com.tannergooding.minecraftsharp;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;

public interface nethost_library_windows extends StdCallLibrary
{
    public static final String JNA_LIBRARY_NAME = "nethost";
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(nethost_library_windows.JNA_LIBRARY_NAME);
    public static final nethost_library_windows INSTANCE = (nethost_library_windows)Native.loadLibrary(nethost_library_windows.JNA_LIBRARY_NAME, nethost_library_windows.class);

    public static class get_hostfxr_parameters extends Structure
    {
        public Pointer size;
        public WString assembly_path;
        public WString dotnet_root;

        public get_hostfxr_parameters()
        {
            super();
        }

        public get_hostfxr_parameters(Pointer peer)
        {
            super(peer);
        }

        public get_hostfxr_parameters(Pointer size, WString assembly_path, WString dotnet_root)
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

    int get_hostfxr_path(CharBuffer buffer, PointerByReference buffer_size, Pointer parameters);
}
