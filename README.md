# EasyCycle
EasyCycle is a Paper plugin for Minecraft that facilitates cycling villager trades! In order to use it in-game, Either shift-right click or press the F key when looking at the villager (Hotkeys depend on the config, default is shift-right click), and the villager will refresh trades!

![javaw_63PdhFcFw2.gif](https://github.com/Jandin12/EasyCycle/blob/main/images/javaw_63PdhFcFw2.gif)

### How to install?

There are two methods on how to install EasyCycle to your server. One is by downloading the plugin either from Modrinth or here **_(By FAR the easiest method)_**, or you can build it from source from the zip file containing all the code for the plugin **_(Harder method, but doable)_**. The instructions on how to get the plugin will be down below.

### Easiest Method

1. Go to the [EasyCycle page on Modrinth.](https://modrinth.com/project/easycycle)
2. Download the latest version of EasyCycle _**(Version 1.0 is the latest as of this release)**_ from the _**"Versions"**_ page
3.  Place the plugin jar file in your server's _**"plugins"**_ folder.
4. Turn on your server or restart it if it's already on.
5. Let EasyCycle generate its configuration files.
6. Once server has finished starting and it's joinable, EasyCycle is ready!

### Harder Method (Building from source)

### Prerequisites: 

- You have Apache Maven installed on your operating system, from the [Apache Maven website.](https://maven.apache.org/index.html)
- You have JDK 21 or 25 installed on your operating system, from the [Oracle Java website.](https://www.oracle.com/java/technologies/downloads/)  _**Please note that I used JDK 25 to build this, so it is recommended to use JDK 25, as it is the latest version, however JDK 21 should work fine as well.**_
- You downloaded the source zip file, linked down below in release.

1. Confirm you have JDK installed on your operating system by doing `java --version` in Windows Command Prompt or your Linux distribution's terminal. It should return something like this: 
`java 25.0.1 2025-10-21 LTS
Java(TM) SE Runtime Environment (build 25.0.1+8-LTS-27)
Java HotSpot(TM) 64-Bit Server VM (build 25.0.1+8-LTS-27, mixed mode, sharing)`
_**The latest, as of writing this, should be JDK 25.0.1 for JDK 25**_
2. Confirm you have Apache Maven installed on your operating system by doing `mvn --version` in Windows Command Prompt or your Linux distribution's terminal. It should return something like this:
`Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: (The home path for maven)
Java version: 25.0.1, vendor: Oracle Corporation, runtime: (The home path for JDK)
Default locale: (Your default locale), platform encoding: (Your default encoding)
OS name: (Your OS name)
_**The latest, as of writing this, should be Apache Maven 3.9.11 for Maven**_

### The Steps:

### For Windows:

1. On cmd (confirm you're running it as administrator), `cd` to the source zip file, once extracted.
2. Once inside the source folder, run `dir` to confirm you see the `src` folder and `pom.xml`.
3. Run `mvn clean package` in the source directory.
4. Once it's complete, the plugin jar can be found in the `target` folder!

### For Linux:
1. In your terminal, `cd` to the source zip file, once extracted. 
2. Run `ls -F` to confirm you see the `src/` folder and `pom.xml` file.
3. Run `mvn clean package` in the source directory.
4. Once it's complete, the plugin jar can be found in the `target/` folder!

### How to use EasyCycle:

Once the plugin is installed on your server, the only commands you need to learn is `/easycycle version` and `/easycycle reload`, which are self-explanatory, and you can also use the shorthand, `/ec`. EasyCycle activates by targeting a villager with a specific interaction.

The Action: Cycle a Villager's Trades

1. Find a Novice-level Villager (a villager that hasn't been traded with yet).
2. Hold the Shift key (sneak/crouch).
3. Right-Click the villager while sneaking.

Note: If the villager is already traded with (locked), you cannot cycle their trades.

Each Shift + Right-Click action will instantly reset the villager's current trades, allowing them to reroll their offers without you having to break their workstation.

### Customizing the Trigger (The Hotkey)

IMPORTANT NOTE: You can easily change this default interaction! If you prefer pressing the 'F' key (Swap Hands) to cycle trades instead of Shift + Right-Click, open the plugin's configuration file:

1. Open `plugins/EasyCycle/config.yml`.
2. Change `trigger-mode: SHIFT_CLICK` to `trigger-mode: SWAP_KEY`.
3. Run `/easycycle reload` or `/ec reload` on your server console or in-game.
