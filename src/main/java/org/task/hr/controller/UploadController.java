package org.task.hr.controller;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.opencsv.CSVReader;
import org.task.hr.entity.Person;
import org.task.hr.repository.PersonRepository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException, CsvException {
        List<String[]> csvList = new ArrayList<>();
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/index.html";
        } else {
            try (CSVReader reader = new CSVReader(new StringReader(new String(file.getBytes())))) {
                csvList = reader.readAll();
                for (String[] line:removeCsvHeader(csvList) ) {
                    personRepository.save(new Person(line[0], line[1], line[2]));
                }
            }
                redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
        }
        return "redirect:/index.html";
    }

    /*
     *   Method to remove csv header caption if exists
     */
    private List<String[]> removeCsvHeader (List<String[]> csvList){
        List<String[]> csvListReturn = new ArrayList<>();
        for (String[] line: csvList) {
            if (!line[0].toLowerCase().equals("name")) {
                csvListReturn.add(line);
            }
        }
        return csvListReturn;
    }

}
