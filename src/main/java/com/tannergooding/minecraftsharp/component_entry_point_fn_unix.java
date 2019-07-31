package com.tannergooding.minecraftsharp;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface component_entry_point_fn_unix extends Callback
{
    int apply(Pointer arg, int arg_size_in_bytes);
}
