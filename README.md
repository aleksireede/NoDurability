# NoDurability

Enhance your Minecraft server with **NoDurability**! This lightweight Paper plugin lets players manage the durability of their items effortlessly. With a simple command, players can instantly restore the durability of their tools, weapons, and armor, keeping their equipment in perfect condition.

> **Compatibility:** Supports **Paper 26.2 and newer**.

## Features

- **Durability Reset** – Execute `/removedurability` to instantly restore the durability of all supported items in your inventory.
- **Single Material Reset** – Restore the durability of only one type of item with `/removedurability <material>`.
- **Configurable Exclusions** – Exclude specific materials from durability changes through the configuration.
- **Permission-Based Exclusions** – Prevent selected players or groups from modifying specific materials using permissions.
- **Prevent Item Combusting** – Optionally prevent dropped items from being destroyed by fire or lava.
- **PlaceholderAPI Support** – Display the durability of the currently held item using the provided placeholder.

## Commands

| Command | Description |
|---------|-------------|
| `/removedurability` | Restores the durability of all supported items in your inventory. |
| `/removedurability <material>` | Restores the durability of the specified material only. |

## Permissions

| Permission | Description |
|------------|-------------|
| `removedurability.*` | Grants all plugin permissions. |
| `removedurability:rm` | Allows using the `/removedurability` command. |
| `nodurability.exclude.<material>` | Prevents the specified material from being modified. |

## Requirements

- **Paper 26.2 or newer**
- Java version required by your Paper server
