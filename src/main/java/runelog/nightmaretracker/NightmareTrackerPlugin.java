package runelog.nightmaretracker;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import runelog.nightmaretracker.Constants;

@Slf4j
@PluginDescriptor(
	name = "Nightmare Tracker"
)
public class NightmareTrackerPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private NightmareTrackerConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	@Subscribe
	protected void onChatMessage(ChatMessage chatMsg)
	{

	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	NightmareTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(NightmareTrackerConfig.class);
	}

	private boolean IsInNightmare() {
		if (this.client.getGameState() != GameState.LOGGED_IN)
			return false;
		WorldPoint wp = this.client.getLocalPlayer().getWorldLocation();
		int x = wp.getX() - this.client.getBaseX();
		int y = wp.getY() - this.client.getBaseY();
		int template = this.client.getInstanceTemplateChunks()[this.client.getPlane()][x /
				8][y / 8];
		return ((template & 0x3FE3FC0) == 58205888);
	}
}
