# Gravity
Intro
---
> Gravity is a plugin that allows you to change the gravity physics of Minecraft worlds.

**The plugin is for use on the InfinityMiners Minecraft server.**

Setup
---
#### config.yml
```
# InfinityMiners Gravity
# jstnf - 2019
# Configuration File

gravity:
  # Should Gravity deploy its effects on server start?
  # Disable if you want to manually toggle gravity on with /gravity toggle
  enabled-on-start: true

  # The worlds with gravity being affected.
  # format: WORLD:GRAVITY
  #
  # Example:
  #
  # worlds:
  #   - world:0.2 (A world called 'world' has 0.2 gravity)
  #   - Space:0   (A world called 'Space' has 0 gravity)
  worlds:
    - ''

  # Do not change this value!
  # Doing so can regenerate your config!
  config-version: 1
```

Usage
---
| Command | Permission | Description |
| --- | --- | --- |
| **/gravity** | *gravity.gravity* | Default command for the *Gravity* plugin |
| **/gravity toggle** | *gravity.toggle* | Toggle the manipulation of gravity |