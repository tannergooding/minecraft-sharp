package com.tannergooding.minecraftsharp;

import com.sun.jna.Function;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Logger;

@Mod(modid = MinecraftSharp.MODID, name = MinecraftSharp.NAME, version = MinecraftSharp.VERSION)
public class MinecraftSharp
{
    private static final String runtime_config_path = "MinecraftSharp.runtimeconfig.json";
    private static final String assembly_path = "MinecraftSharp.dll";
    private static final String type_name = "MinecraftSharp.MyClass, MinecraftSharp";
    private static final String method_name = "GetHelloWorldString";
    private static final String delegate_type_name = "MinecraftSharp.EntryPointDelegate, MinecraftSharp";

    public static final String MODID = "minecraftsharp";
    public static final String NAME = "MinecraftSharp";
    public static final String VERSION = "0.1";

    private static Logger logger;
    private static coreclr coreclr_host;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        coreclr_host = SystemUtils.IS_OS_WINDOWS ? new coreclr.windows_host(logger) : new coreclr.unix_host(logger);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
        throws Exception
    {
        Function callback = coreclr_host.load_assembly_and_get_callback(runtime_config_path, assembly_path, type_name, method_name, delegate_type_name);

        String result = (String)callback.invoke(String.class, new Object[] {
        });

        logger.info("Managed callback returned: {}", result);
    }
}
