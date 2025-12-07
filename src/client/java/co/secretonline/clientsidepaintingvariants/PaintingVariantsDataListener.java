package co.secretonline.clientsidepaintingvariants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.slf4j.Logger;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class PaintingVariantsDataListener implements PreparableReloadListener {
	private static Logger LOGGER = ClientSidePaintingVariants.LOGGER;

	@Override
	public CompletableFuture<Void> reload(SharedState store, Executor prepareExecutor,
			PreparationBarrier reloadSynchronizer,
			Executor applyExecutor) {
		return CompletableFuture
				.supplyAsync(
						() -> this.getPaintingsFromData(store.resourceManager()),
						prepareExecutor)
				.thenCompose(reloadSynchronizer::wait)
				.thenAcceptAsync(
						(paintings) -> PaintingsInfo.getInstance().setRegistryPaintings(paintings),
						applyExecutor);
	}

	/**
	 * Note: This method is similar to the one in
	 * {@link PaintingVariantsResourceListener}.
	 * If making changes here, be sure to also change there too.
	 */
	private Map<ResourceLocation, PaintingVariant> getPaintingsFromData(ResourceManager resourceManager) {
		Map<ResourceLocation, PaintingVariant> paintings = new HashMap<>();

		// Load all files from resource packs that should contain painting variants.
		// Vanilla paintings shouldn't appear in this list, as this is reading from
		// resources and not data.
		var allVariantJsonFiles = resourceManager.listResources(
				"painting_variant",
				identifier -> identifier.getPath().endsWith(".json"));

		allVariantJsonFiles.forEach((identifier, resource) -> {
			try (var reader = resource.openAsReader()) {
				JsonObject data = GsonHelper.parse(reader).getAsJsonObject();
				int width = data.get("width").getAsInt();
				int height = data.get("height").getAsInt();

				ResourceLocation assetId = ResourceLocation.parse(data.get("asset_id").getAsString());

				// TODO: Read text components
				paintings.put(identifier, new PaintingVariant(width, height, assetId, Optional.empty(), Optional.empty()));
			} catch (IOException ex) {
				LOGGER.warn("Failed to read data for registry painting variant " + identifier.toString() + ". Skipping");
			} catch (Exception ex) {
				LOGGER.warn("Error while reading registry painting variant " + identifier.toString() + ". Skipping");
			}
		});

		return paintings;
	}
}
