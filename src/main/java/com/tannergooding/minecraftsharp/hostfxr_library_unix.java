package com.tannergooding.minecraftsharp;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;
import java.util.Arrays;
import java.util.List;

public interface hostfxr_library_unix extends Library
{
    public static interface hostfxr_delegate_type
    {
        public static final int hdt_com_activation = 0;
        public static final int hdt_load_in_memory_assembly = 1;
        public static final int hdt_winrt_activation = 2;
        public static final int hdt_com_register = 3;
        public static final int hdt_com_unregister = 4;
        public static final int hdt_load_assembly_and_get_function_pointer = 5;
    }

    public static class hostfxr_initialize_parameters extends Structure
    {
        public Pointer size;
        public String host_path;
        public String dotnet_root;

        public hostfxr_initialize_parameters()
        {
            super();
        }

        public hostfxr_initialize_parameters(Pointer peer)
        {
            super(peer);
        }

        public hostfxr_initialize_parameters(Pointer size, String host_path, String dotnet_root)
        {
            super();

            this.size = size;
            this.host_path = host_path;
            this.dotnet_root = dotnet_root;
        }

        protected List<String> getFieldOrder()
        {
            return Arrays.asList("size", "host_path", "dotnet_root");
        }

        public static class ByReference extends hostfxr_initialize_parameters implements Structure.ByReference
        {
        }

        public static class ByValue extends hostfxr_initialize_parameters implements Structure.ByValue
        {
        }
    }

    public interface hostfxr_error_writer_fn extends Callback
    {
        void apply(String message);
    }

    public interface hostfxr_get_runtime_property_value_fn extends Callback
    {
        int apply(Pointer host_context_handle, String name, PointerByReference value);
    }

    int hostfxr_main(int argc, PointerByReference argv);

    int hostfxr_main_startupinfo(int argc, PointerByReference argv, String host_path, String dotnet_root, String app_path);

    hostfxr_error_writer_fn hostfxr_set_error_writer(hostfxr_error_writer_fn error_writer);

    int hostfxr_initialize_for_dotnet_command_line(int argc, PointerByReference argv, hostfxr_initialize_parameters.ByReference parameters, PointerByReference host_context_handle);

    int hostfxr_initialize_for_runtime_config(String runtime_config_path, hostfxr_initialize_parameters.ByReference parameters, PointerByReference host_context_handle);

    int hostfxr_set_runtime_property_value(Pointer host_context_handle, String name, String value);

    int hostfxr_get_runtime_properties(Pointer host_context_handle, PointerByReference count, PointerByReference keys, PointerByReference values);

    int hostfxr_run_app(Pointer host_context_handle);

    int hostfxr_get_runtime_delegate(Pointer host_context_handle, int type, PointerByReference delegate);

    int hostfxr_close(Pointer host_context_handle);
}
