package org.cms.controller;

import lombok.RequiredArgsConstructor;
import org.cms.dto.CustomerDTO;
import org.cms.entity.Customer;
import org.cms.service.CustomerService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job customerUploadJob;

    private final CustomerService customerService;

    @PostMapping("/Add")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer savedCustomer= customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/Update/{Nic}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String Nic, @RequestBody CustomerDTO customerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(Nic, customerDTO);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(page, size);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {

            String tempFilePath = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            file.transferTo(new File(tempFilePath));


            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", tempFilePath)
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();


            jobLauncher.run(customerUploadJob, jobParameters);

            return ResponseEntity.ok("Excel upload process started successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }
}
