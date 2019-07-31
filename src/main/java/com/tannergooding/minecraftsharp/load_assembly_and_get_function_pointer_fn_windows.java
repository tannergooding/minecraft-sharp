package com.tannergooding.minecraftsharp;

import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public interface load_assembly_and_get_function_pointer_fn_windows extends StdCallCallback
{
	int apply(WString assembly_path, WString type_name, WString method_name, WString delegate_type_name, Pointer reserved, PointerByReference delegate);
}
