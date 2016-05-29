package net.shadowfacts.infiniocean.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shadowfacts
 */
public class DimensionTypeTransformer implements IClassTransformer, Opcodes {

	private static final Map<String, String> replacements = new HashMap<>();

	static {
		replacements.put("net.minecraft.world.WorldProviderSurface", "Lnet/shadowfacts/infiniocean/overworld/ProviderOverworld;");
		replacements.put("net.minecraft.world.WorldProviderHell", "Lnet/shadowfacts/infiniocean/nether/ProviderNether;");
		replacements.put("net.minecraft.world.WorldProviderEnd", "Lnet/shadowfacts/infiniocean/end/ProviderEnd;");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] bytes) {
		boolean obfuscated = !name.equals(transformedName);
		if ("net.minecraft.world.DimensionType".equals(transformedName)) {

			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(bytes);
			classReader.accept(classNode, 0);

			transform(classNode, obfuscated);

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();
		}

		return bytes;
	}

	private void transform(ClassNode classNode, boolean obfuscated) {
		final String CLINIT = "<clinit>";

		for (MethodNode method : classNode.methods) {

			if (CLINIT.equals(method.name)) {

				for (AbstractInsnNode instruction : method.instructions.toArray()) {

					if (instruction.getOpcode() == LDC) {
						LdcInsnNode ldc = (LdcInsnNode)instruction;
						if (ldc.cst instanceof Type) {
							Type type = (Type)ldc.cst;
							if (replacements.containsKey(type.getClassName())) {
								ldc.cst = Type.getType(replacements.get(type.getClassName()));
							}
						}
					}

				}

			}

		}
	}

}
