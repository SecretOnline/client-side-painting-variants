package co.secretonline.clientsidepaintingvariants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.network.chat.Component;

public class ClientSidePaintingVariantsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloader(
				ClientSidePaintingVariants.id("resource-listener"),
				new PaintingVariantsResourceListener());

		ResourceLoader.get(PackType.SERVER_DATA).registerReloader(
				ClientSidePaintingVariants.id("data-listener"),
				new PaintingVariantsDataListener());

		FabricLoader.getInstance()
				.getModContainer(ClientSidePaintingVariants.MOD_ID)
				.ifPresent(container -> {
					ResourceManagerHelper.registerBuiltinResourcePack(
							ClientSidePaintingVariants.id("logo"),
							container,
							Component.literal("Client Side Painting Variants - Logo"),
							ResourcePackActivationType.NORMAL);
				});
	}
}
