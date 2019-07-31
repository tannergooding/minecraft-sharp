package com.tannergooding.minecraftsharp;

import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.PointerByReference;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.apache.logging.log4j.Logger;

public abstract class coreclr
{
    private static final int MAX_PATH = 260;

    protected Logger logger;

    protected coreclr(Logger logger)
    {
        this.logger = logger;
    }

    public static class unix_host extends coreclr
    {
        private hostfxr_library_unix hostfxr_library;

        public unix_host(Logger logger)
        {
            super(logger);
        }

        public Function load_assembly_and_get_callback(String runtime_config_path, String assembly_path, String type_name, String method_name, String delegate_type_name)
            throws Exception
        {
            if (!load_hostfxr())
            {
                throw new Exception("Failed to resolve hostfxr");
            }

            File runtime_config_path_file = new File(runtime_config_path).getAbsoluteFile();
            logger.info("Resolved the runtime.config.json to: {}", runtime_config_path_file.toString());

            Function load_assembly_and_get_function_pointer_fn = get_load_assembly_and_get_function_pointer_fn(runtime_config_path_file.toString());

            if (load_assembly_and_get_function_pointer_fn == Pointer.NULL)
            {
                throw new Exception("Failed to resolve load_assembly_and_get_function_pointer");
            }

            Pointer managed_fn = Pointer.NULL;
            PointerByReference ptr_managed_fn = new PointerByReference(managed_fn);

            File assembly_path_file = new File(assembly_path).getAbsoluteFile();
            logger.info("Resolved the .net library to: {}", assembly_path_file.toString());

            logger.info("Loading type: {}", type_name);
            logger.info("Locating method: {}", method_name);
            logger.info("Matching delegate: {}", delegate_type_name);

            int rc = (int)load_assembly_and_get_function_pointer_fn.invokeInt(new Object[] {
                assembly_path_file.toString(),
                type_name,
                method_name,
                delegate_type_name,
                Pointer.NULL,
                ptr_managed_fn
            });

            managed_fn = ptr_managed_fn.getValue();

            if (rc != 0 || managed_fn == Pointer.NULL)
            {
                String message = String.format("load_assembly_and_get_function_pointer failed with: {}", rc);
                throw new Exception(message);
            }

            return Function.getFunction(managed_fn, Function.C_CONVENTION);
        }

        protected Function get_load_assembly_and_get_function_pointer_fn(String runtime_config_path)
        {
            Pointer load_assembly_and_get_function_pointer_fn = Pointer.NULL;

            hostfxr_library_unix.hostfxr_initialize_parameters.ByReference parameters = new hostfxr_library_unix.hostfxr_initialize_parameters.ByReference();

            Pointer host_context_handle = Pointer.NULL;
            PointerByReference ptr_host_context_handle = new PointerByReference(host_context_handle);

            int rc = hostfxr_library.hostfxr_initialize_for_runtime_config(runtime_config_path, parameters, ptr_host_context_handle);
            host_context_handle = ptr_host_context_handle.getValue();

            if (rc != 0 || host_context_handle == Pointer.NULL)
            {
                logger.info("hostfxr_initialize_for_runtime_config failed with: {}", rc);
            }
            else
            {
                PointerByReference ptr_load_assembly_and_get_function_pointer_fn = new PointerByReference(load_assembly_and_get_function_pointer_fn);

                rc = hostfxr_library.hostfxr_get_runtime_delegate(host_context_handle, hostfxr_library_unix.hostfxr_delegate_type.hdt_load_assembly_and_get_function_pointer, ptr_load_assembly_and_get_function_pointer_fn);
                load_assembly_and_get_function_pointer_fn = ptr_load_assembly_and_get_function_pointer_fn.getValue();

                if (rc != 0 || load_assembly_and_get_function_pointer_fn == Pointer.NULL)
                {
                    logger.info("hostfxr_get_runtime_delegate failed with: {}", rc);
                }
            }

            hostfxr_library.hostfxr_close(host_context_handle);
            return Function.getFunction(load_assembly_and_get_function_pointer_fn, Function.C_CONVENTION);
        }

        protected boolean load_hostfxr()
        {
            logger.info("MinecraftSharp is running on Unix");

            ByteBuffer buffer = ByteBuffer.allocate(MAX_PATH);

            Pointer buffer_size = Pointer.createConstant(MAX_PATH);
            PointerByReference ptr_buffer_size = new PointerByReference(buffer_size);

            if (nethost_library_unix.INSTANCE.get_hostfxr_path(buffer, ptr_buffer_size, Pointer.NULL) != 0)
            {
                return false;
            }

            String hostfxr_path = new String(buffer.array());
            logger.info("Resolved hostfxr to: {}", hostfxr_path);

            hostfxr_library = (hostfxr_library_unix)Native.loadLibrary(hostfxr_path, hostfxr_library_unix.class);
            return true;
        }
    }

    public static class windows_host extends coreclr
    {
        private hostfxr_library_windows hostfxr_library;

        public windows_host(Logger logger)
        {
            super(logger);
        }

        public Function load_assembly_and_get_callback(String runtime_config_path, String assembly_path, String type_name, String method_name, String delegate_type_name)
            throws Exception
        {
            if (!load_hostfxr())
            {
                throw new Exception("Failed to resolve hostfxr");
            }

            File runtime_config_path_file = new File(runtime_config_path).getAbsoluteFile();
            logger.info("Resolved the runtime.config.json to: {}", runtime_config_path_file.toString());

            Function load_assembly_and_get_function_pointer_fn = get_load_assembly_and_get_function_pointer_fn(runtime_config_path_file.toString());

            if (load_assembly_and_get_function_pointer_fn == Pointer.NULL)
            {
                throw new Exception("Failed to resolve load_assembly_and_get_function_pointer");
            }

            Pointer managed_fn = Pointer.NULL;
            PointerByReference ptr_managed_fn = new PointerByReference(managed_fn);

            File assembly_path_file = new File(assembly_path).getAbsoluteFile();
            logger.info("Resolved the .net library to: {}", assembly_path_file.toString());

            logger.info("Loading type: {}", type_name);
            logger.info("Locating method: {}", method_name);
            logger.info("Matching delegate: {}", delegate_type_name);

            int rc = (int)load_assembly_and_get_function_pointer_fn.invokeInt(new Object[] {
                new WString(assembly_path_file.toString()),
                new WString(type_name),
                new WString(method_name),
                new WString(delegate_type_name),
                Pointer.NULL,
                ptr_managed_fn
            });

            managed_fn = ptr_managed_fn.getValue();

            if (rc != 0 || managed_fn == Pointer.NULL)
            {
                String message = String.format("load_assembly_and_get_function_pointer failed with: {}", rc);
                throw new Exception(message);
            }

            return Function.getFunction(managed_fn, Function.C_CONVENTION);
        }

        protected Function get_load_assembly_and_get_function_pointer_fn(String runtime_config_path)
        {
            Pointer load_assembly_and_get_function_pointer_fn = Pointer.NULL;

            hostfxr_library_windows.hostfxr_initialize_parameters.ByReference parameters = new hostfxr_library_windows.hostfxr_initialize_parameters.ByReference();

            Pointer host_context_handle = Pointer.NULL;
            PointerByReference ptr_host_context_handle = new PointerByReference(host_context_handle);

            int rc = hostfxr_library.hostfxr_initialize_for_runtime_config(new WString(runtime_config_path), parameters, ptr_host_context_handle);
            host_context_handle = ptr_host_context_handle.getValue();

            if (rc != 0 || host_context_handle == Pointer.NULL)
            {
                logger.info("hostfxr_initialize_for_runtime_config failed with: {}", rc);
            }
            else
            {
                PointerByReference ptr_load_assembly_and_get_function_pointer_fn = new PointerByReference(load_assembly_and_get_function_pointer_fn);

                rc = hostfxr_library.hostfxr_get_runtime_delegate(host_context_handle, hostfxr_library_windows.hostfxr_delegate_type.hdt_load_assembly_and_get_function_pointer, ptr_load_assembly_and_get_function_pointer_fn);
                load_assembly_and_get_function_pointer_fn = ptr_load_assembly_and_get_function_pointer_fn.getValue();

                if (rc != 0 || load_assembly_and_get_function_pointer_fn == Pointer.NULL)
                {
                    logger.info("hostfxr_get_runtime_delegate failed with: {}", rc);
                }
            }

            hostfxr_library.hostfxr_close(host_context_handle);
            return Function.getFunction(load_assembly_and_get_function_pointer_fn, Function.ALT_CONVENTION);
        }

        protected boolean load_hostfxr()
        {
            logger.info("MinecraftSharp is running on Windows");

            CharBuffer buffer = CharBuffer.allocate(MAX_PATH);

            Pointer buffer_size = Pointer.createConstant(MAX_PATH);
            PointerByReference ptr_buffer_size = new PointerByReference(buffer_size);

            if (nethost_library_windows.INSTANCE.get_hostfxr_path(buffer, ptr_buffer_size, Pointer.NULL) != 0)
            {
                return false;
            }

            String hostfxr_path = new String(buffer.array());
            logger.info("Resolved hostfxr to: {}", hostfxr_path);

            hostfxr_library = (hostfxr_library_windows)Native.loadLibrary(hostfxr_path, hostfxr_library_windows.class);
            return true;
        }
    }

    public abstract Function load_assembly_and_get_callback(String runtime_config_path, String assembly_path, String type_name, String method_name, String delegate_type_name)
        throws Exception;

    protected abstract Function get_load_assembly_and_get_function_pointer_fn(String runtime_config_path);

    protected abstract boolean load_hostfxr();
}
