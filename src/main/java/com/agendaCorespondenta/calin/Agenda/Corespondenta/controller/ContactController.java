package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.ContactService;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
	final UserService userService;
	final ContactService contactService;

	@GetMapping("/add")
	public String addContact(Model model, @ModelAttribute("user") UserEntity user) {
		UserEntity realUser = userService.findByEmail(user.getEmail());
		model.addAttribute("user", realUser);
		return "fragments/addNewContactForm";
	}

	@PostMapping("/add")
	public String addContact(@ModelAttribute Contact contact, String userEmail, Model model, RedirectAttributes redirectAttributes) {

		System.out.println(contact);
		System.out.println(userEmail);
		UserEntity currentUser = userService.findByEmail(userEmail);

		if (contactService.existsByEmail(contact.getEmail())) {
			model.addAttribute("hasFormError", true);
			model.addAttribute("addFormError", "Contact with that email already exists!");
			return "redirect:/invalidAddForm";
		}

		contactService.saveContact(contact, currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("isNewContact", true);
		model.addAttribute("contact", contact);
		redirectAttributes.addFlashAttribute("successMessage", "Contact added successfully!");
		return "redirect:/";
	}

	@GetMapping("/invalidAddForm")
	public String invalidAddForm(Model model) {
		return "fragments/addNewContactForm";
	}

	@GetMapping("/edit/{email}")
	public String updateUser(@PathVariable String email, Model model) {
		Contact contact = contactService.findByEmail(email);
		model.addAttribute("contact", contact);
		return "fragments/editContact";
	}

	@PostMapping("/update")
	public String updateUser(Contact contact, RedirectAttributes redirectAttributes) {
		contactService.updateContact(contact);
		redirectAttributes.addFlashAttribute("successMessage", "Contact updated successfully!");
		return "redirect:/";
	}
}
