package io.github.mcengine.papermc.skill.engine;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.Metrics;
import io.github.mcengine.common.skill.MCEngineSkillCommon;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main PaperMC plugin class for MCEngineSkill.
 */
public class MCEngineSkillPaperMC extends JavaPlugin {

    /**
     * Called when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        new Metrics(this, 26383);
        saveDefaultConfig(); // Save config.yml if it doesn't exist

        boolean enabled = getConfig().getBoolean("enable", false);
        if (!enabled) {
            getLogger().warning("Plugin is disabled in config.yml (enable: false). Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MCEngineSkillCommon api = new MCEngineSkillCommon(this);

        // Load extensions
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.skill.extension.library.IMCEngineSkillLibrary",
            "libraries",
            "Library"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.skill.extension.api.IMCEngineSkillAPI",
            "apis",
            "API"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.skill.extension.agent.IMCEngineSkillAgent",
            "agents",
            "Agent"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.skill.extension.addon.IMCEngineSkillAddOn",
            "addons",
            "AddOn"
        );
        MCEngineCoreApi.loadExtensions(
            this,
            "io.github.mcengine.api.skill.extension.dlc.IMCEngineSkillDLC",
            "dlcs",
            "DLC"
        );

        // Check for plugin updates
        MCEngineCoreApi.checkUpdate(
            this,
            getLogger(),
            "github",
            "MCEngine-Engine",
            "skill",
            getConfig().getString("github.token", "null")
        );
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic (if any) can go here
    }
}
