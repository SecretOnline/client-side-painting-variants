# Client side painting variants

Use resource packs to add new types of painting without removing any existing paintings, entirely on the client side!

![Title and logo on top of a field of paintings. Some paintings are the default paintings, but others are custom.](https://cdn.modrinth.com/data/T1MOUdih/images/deebe09e46f70b2a146098f3b09e650757f3b666.png)

This mod requires [Fabric API](https://modrinth.com/mod/fabric-api), so make sure it's installed with this mod.

## Wait, how is this different from 1.21's painting variants?

Minecraft `1.21` adds painting variants that rely on data packs to specify new variants. Unfortunately this means that these paintings must be defined per world, or set up by the server's admin. What this mod does is extend that idea further, allowing resource packs to define more painting variants for any players using the mod on their clients. Clients without the mod just see the regular paintings.

This does lead to the one limitation of this mod: it can't add new sizes of paintings, just add more variants to existing sizes (whether they're defined by Minecraft or a data pack).

## How does this work on multiplayer?

- Players without the mod will see the original paintings as sent by the server.
- Players with the mod will see custom paintings in addition to the original paintings.
  - If two players with the mod installed have the same resource packs installed, then they will see the same paintings.
  - This mod will never override a vanilla painting with another vanilla painting, so if you see a vanilla painting then players without the mod will see that same painting.

![Comparison of viewing the same paintings from an unmodded and modded client. Custom paintings are only visible on the modded client, but show up as normal paintings on the unmodded client.](https://cdn.modrinth.com/data/T1MOUdih/images/e2cd94ce1d29a033689e3bfab5f43e235334327d.png)

## For resource/data pack developers

Players with this mod installed can have your paintings added into the pool of available paintings wherever they go with your resource pack, and not just on servers with the data pack. Since this mod doesn't remove any of Minecraft's default paintings, it's a nice non-destructive way to have your artwork integrated into the game.

There are only two requirements for this mod to work.

1. Ensure your painting textures are in your resource pack's `assets/<namespace>/textures/painting/` folder (where the game's default paintings are, so hopefully this is where you've put your painting textures too).
2. Add the painting variant JSON files to your resource pack's `assets/<namespace>/painting_variant/` (note: no trailing `s`) folder.
   - These are the same format as adding painting variants in a data pack.
     - `width`: Width of the painting in blocks. Must be in the range of 1-16.
     - `height`: Height of the painting in blocks. Must be in the range of 1-16.
     - `asset_id`: Identifier of the texture in the `paintings` atlas. `<namespace>/<filename-without-ext>`
   - Ref: [24w18a changelog](https://www.minecraft.net/en-us/article/minecraft-snapshot-24w18a).

<details>
<summary>Example resource pack structure</summary>

```txt
resourcepack.zip
├─pack.mcmeta
├─pack.png
└─assets/
  └─<namespace>/
    ├─painting_variant/
    │ ├─<id1>.json
    │ └─<id2>.json
    └ textures/
      └─painting/
        ├─<id1>.png
        └─<id2>.png
```

</details>

This as similar as possible to 1.21's painting variant system, which is driven by data packs. This mod takes the two folders from the two different types of pack and puts them together.

<details>
<summary>Equivalent data and resource pack in Vanilla</summary>

```txt
datapack.zip
├─pack.mcmeta
└─data/
  └─<namespace>/
    └─painting_variant/
      ├─<id1>.json
      └─<id2>.json

resourcepack.zip
├─pack.mcmeta
├─pack.png
└─assets/
  └─<namespace>/
    └ textures/
      └─painting/
        ├─<id1>.png
        └─<id2>.png
```

</details>

I'd love to make this a no-effort thing for pack developers (which would also have the benefit of working out-of-the-box), but unfortunately the resource/data pack split makes the ideal version of this project impossible. Instead, I think this is the next best thing. I hope that it's a small thing you can just do without having to think about it too much.

## License

This mod is licensed under the Mozilla Public License 2.0. You may use this mod in your mod packs. Source code is included in a separate `sources.jar` file alongside every release.
