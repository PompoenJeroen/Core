package me.winterguardian.core.entity.custom;

import java.lang.reflect.Field;
import java.util.Map;

import me.winterguardian.core.util.ReflectionUtil;

public enum CustomEntityType
{

	RIDEABLE_SPIDER("Spider", 52, "<base>.rideable.<version>.RideableSpider"),

	RIDEABLE_ZOMBIE("Zombie", 54, "<base>.rideable.<version>.RideableZombie"),
	RIDEABLE_SLIME("Slime", 55, "<base>.rideable.<version>.RideableSlime"),
	RIDEABLE_POLAR_BEAR("PolarBear", 102, "<base>.rideable.<version>.RideablePolarBear"),
	RIDEABLE_LLAMA("Llama", 103, "<base>.rideable.<version>.RideableLlama"),

	RIDEABLE_CAVE_SPIDER("CaveSpider", 59, "<base>.rideable.<version>.RideableCaveSpider"),
	RIDEABLE_SILVERFISH("Silverfish", 60, "<base>.rideable.<version>.RideableSilverfish"),

	RIDEABLE_MAGMA_CUBE("LavaSlime", 62, "<base>.rideable.<version>.RideableMagmaCube"),

	RIDEABLE_GUARDIAN("Guardian", 68, "<base>.rideable.<version>.RideableGuardian"),

	RIDEABLE_PIG("Pig", 90, "<base>.rideable.<version>.RideablePig"),
	RIDEABLE_SHEEP("Sheep", 91, "<base>.rideable.<version>.RideableSheep"),
	RIDEABLE_COW("Cow", 92, "<base>.rideable.<version>.RideableCow"),
	RIDEABLE_CHICKEN("Chicken", 93, "<base>.rideable.<version>.RideableChicken"),
	RIDEABLE_WOLF("Wolf", 95, "<base>.rideable.<version>.RideableWolf"),
	RIDEABLE_MUSHROOM_COW("MushroomCow", 96, "<base>.rideable.<version>.RideableMushroomCow"),

	RIDEABLE_OCELOT("Ocelot", 98, "<base>.rideable.<version>.RideableOcelot"),

	RIDEABLE_RABBIT("Rabbit", 101, "<base>.rideable.<version>.RideableRabbit"),

	NPC_VILLAGER("Villager", 120, "<base>.npc.<version>.NPCVillager"),
	;
	
	private String name;
	private int id;
	private Class<?> customClass;

	CustomEntityType(String name, int id, Class<?> customClass)
	{
		this.name = name;
		this.id = id;
		this.customClass = customClass;
	}

	CustomEntityType(String name, int id, String customClass)
	{
		this.name = name;
		this.id = id;
		try
		{
			this.customClass = Class.forName(customClass.replace("<base>", "me.winterguardian.core.entity.custom").replace("<version>", ReflectionUtil.getVersion()));
		}
		catch(Exception e)
		{
			new Exception("Unsupported minecraft version for custom entities: " + ReflectionUtil.getVersion(), e).printStackTrace();
			this.customClass = null;
		}
	}
 
	public String getName()
	{
		return this.name;
	}
 
	public int getID()
	{
		return this.id;
	}

	public Class<?> getCustomClass()
	{
		return customClass;
	}
	

	@SuppressWarnings("unchecked")
	public static void registerEntities()
    {
    	for (CustomEntityType entity : values())
    	{
		    if(entity.getCustomClass() == null)
			    continue;
		    
            try
            {
            	for(Field field : Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".EntityTypes").getDeclaredFields())
            	{
            		if(!field.isAccessible())
            			field.setAccessible(true);
            		switch(field.getName())
            		{
            		case "d":
            			((Map<Class<?>, String>)field.get(null)).put(entity.getCustomClass(), entity.getName());
            			break;
            		case "f":
            			((Map<Class<?>, Integer>)field.get(null)).put(entity.getCustomClass(), entity.getID());
            			break;
            		}
	                
            	}
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    	
    }

	@SuppressWarnings("unchecked")
	public static void unregisterEntities()
	{
		for (CustomEntityType entity : values())
		{
			if(entity.getCustomClass() == null)
				continue;

			try
			{
				for(Field field : Class.forName("net.minecraft.server." + ReflectionUtil.getVersion() + ".EntityTypes").getDeclaredFields())
				{
					if(!field.isAccessible())
						field.setAccessible(true);
					switch(field.getName())
					{
						case "d":
							((Map<Class<?>, String>)field.get(null)).remove(entity.getCustomClass());
							break;
						case "f":
							((Map<Class<?>, Integer>)field.get(null)).remove(entity.getCustomClass());
							break;
					}

				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}
}
