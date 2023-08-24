NOTE: the experimental projects are stored in the sccpu4.

# Meta Info:
codifiedMR_FQN:
org.bukkit.craftbukkit.inventory.FactoryItemMaterialTest_generalCase_AutoMR
original_MTC_FQS:
org.bukkit.craftbukkit.inventory.FactoryItemMaterialTest.generalCase()
poj_dir:
CobbleSword/NachoSpigot/
original_MTC_class_path:
CobbleSword/NachoSpigot/NachoSpigot-Server/src/test/java/org/bukkit/craftbukkit/inventory/FactoryItemMaterialTest.java

# Codified MR:
```java
public void generalCase_AutoMR(Material material) {
    CraftMetaItem meta = (CraftMetaItem) factory.getItemMeta(material);
    if (meta == null) {
        assertThat(material, is(Material.AIR));
    } else {
        assertTrue(factory.isApplicable(meta, material));
        assertTrue(meta.applicableTo(material));
        meta = meta.clone();
        assertTrue(factory.isApplicable(meta, material));
        assertTrue(meta.applicableTo(material));
    }
}
```

# Annotations:
NOTE: line numbers are of the original MTC file
## MR instance1
relation_assertion: assertTrue(factory.isApplicable(meta, material)), line: 80 
source_input: ['material'], first_invoked_method_name: applicableTo, line: 77 
followUp_input: ['meta'], second_invoked_method_name: clone, line: 79 
## MR instance2
relation_assertion: assertTrue(meta.applicableTo(material)), line: 81 
source_input: ['material'], first_invoked_method_name: applicableTo, line: 77 
followUp_input: ['meta'], second_invoked_method_name: clone, line: 79 


# Original MTC and related fields:
```java
static final ItemFactory factory = CraftItemFactory.instance();
@Parameter(0)
public Material material;

static {
        Material[] local_materials = Material.values();
        List<Material> list = new ArrayList<Material>(local_materials.length);
        for (Material material : local_materials) {
            if (INVALIDATED_MATERIALS.contains(material)) {
                continue;
            }

            list.add(material);
        }
        materials = list.toArray(new Material[list.size()]);
    }


@Test
public void generalCase() {
    CraftMetaItem meta = (CraftMetaItem) factory.getItemMeta(material);
    if (meta == null) {
        assertThat(material, is(Material.AIR));
    } else {
        assertTrue(factory.isApplicable(meta, material));
        assertTrue(meta.applicableTo(material));
        meta = meta.clone();
        assertTrue(factory.isApplicable(meta, material));
        assertTrue(meta.applicableTo(material));
    }
}

```


# Answer Block: 
## How is the understandability of this MR? (1. very difficult 2. difficult 3. easy 4. very easy)
your score: 1
 
## comments (if any): 
```txt
No relevant construction process of the material instance
```