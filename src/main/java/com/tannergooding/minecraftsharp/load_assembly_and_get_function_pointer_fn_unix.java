package com.tannergooding.minecraftsharp;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface load_assembly_and_get_function_pointer_fn_unix extends Callback
{
    int apply(String assembly_path, String type_name, String method_name, String delegate_type_name, Pointer reserved, PointerByReference delegate);
}
