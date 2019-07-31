package com.tannergooding.minecraftsharp;

import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public interface component_entry_point_fn_windows extends StdCallCallback
{
    int apply(Pointer arg, int arg_size_in_bytes);
}
