# <img src="src/main/resources/logo.png" alt="drawing" width="24"/> Contrasting Curses [![Modrinth Project](https://img.shields.io/modrinth/dt/4MInEhik?logo=modrinth&label=Modrinth&style=flat&labelColor=2D2D2D&color=555555)](https://modrinth.com/mod/dynamic-multitools)

⚒️ Adds 6 simple multitool combinations, providing **two tools in one** to clear up hotbar space!<br>
⚖️ Balanced stats allow for **configurably decreased tool speed** and variable durability.<br>
🔗 Retains all **innate functionality** of both original tools (tilling/scraping/stripping etc).<br>
🔨 Upgrade existing tools to **transfer enchantments** and other data over.<br>
🧪 Add custom tool tiers for **data-driven mod support** with a single file!<br>
💭 Loosly inspired by the multitools added in Matcha Flavored

## Mod Support

Several popular modded tool tiers have **builtin compatability**:
- [Aether](https://modrinth.com/mod/aether)
- [Deep Aether](https://modrinth.com/mod/deep-aether)
- [Better Nether](https://modrinth.com/mod/betternether-neoforge)
- [Create Stuff 'N Additions](https://modrinth.com/mod/create-stuff-additions)
- [Create Ethium Reimagined](https://modrinth.com/mod/ethium)
- [Flint Required](https://modrinth.com/mod/flint-required)
- [Unusual End](https://modrinth.com/mod/unusual_end)

Create an issue request to add additional builtin compats

## Questions

> **How do I add custom modded tool tiers?**
> 
> wip

> **How can I change the builtin tool tiers?**
> 
> Use any data pack overriding the same namespaced path as the default data files provided in the mod to modify their values! (ex: `Pack/data/dynamic_multitools/dynamic_multitools/dynamic_tier/<tier>.json`)

> **Is there non-tinted texture support?**
> 
> Yes! By using model overrides with custom model data and the dynamic tier components field you can implement any custom texture variants for your multitool tiers! Read more on the wiki.

> **I changed the configs but my tool hasn't changed!**
>
> Config changes and dynamic tier data pack changes only update on newly crafted/given items. This is due to the dynamic component-driven nature of the multitools which makes existing instances set-in-stone.

## License

Feel free to play, stream, or showcase this pack so long as visible credit is given.  
This project can be packaged into any server or modpack so long as significant modifications are disclosed.  
Do **not** redistribute or reupload this pack or its source code without permission.  
Please link to one of the official pack pages instead of redistributing files.  

*Copyright © 2026 RoarkCats.*  
*All rights reserved.* 
