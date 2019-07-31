using System;
using System.Runtime.InteropServices;

namespace MinecraftSharp
{
    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    public delegate string EntryPointDelegate();

    public static class MyClass
    {
        public static string GetHelloWorldString() => "Hello, from .NET Core 3.0!";
    }
}
