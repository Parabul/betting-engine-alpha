package kz.nmbet.betradar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class CodeGen {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Collection<File> entities = FileUtils.listFiles(new File(
				"D:\\project\\nm\\betting-engine-alpha\\src\\main\\java\\kz\\nmbet\\betradar\\dao\\domain\\entity"),
				new String[]{"java"}, false);
		Collection<File> repositoryFiles = FileUtils.listFiles(new File(
				"D:\\project\\nm\\betting-engine-alpha\\src\\main\\java\\kz\\nmbet\\betradar\\dao\\repository"),
				new String[]{"java"}, false);
		List<String> repositories = new ArrayList<String>();
		for (File repositoryFile : repositoryFiles) {
			String name = repositoryFile.getName().replace(".java", "");
			repositories.add(name);
		}

		System.out.println(entities);
		for (File file : entities) {
			String name = file.getName().replace(".java", "");
			String className = name + "Repository";
			if (repositories.contains(className))
				continue;
			String template = IOUtils
					.toString(new FileInputStream(
							"D:\\project\\nm\\betting-engine-alpha\\src\\main\\java\\kz\\nmbet\\betradar\\dao\\repository\\GlMatchEntityRepository.java"));
			String content = template.replace("GlMatchEntity", name);
			FileUtils.write(new File(
					"D:\\project\\nm\\betting-engine-alpha\\src\\main\\java\\kz\\nmbet\\betradar\\dao\\repository\\"
							+ className + ".java"), content);
		}

	}

}
