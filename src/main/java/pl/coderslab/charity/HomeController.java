package pl.coderslab.charity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final InstitutionRepository institutionRepository;
    private final DonationRepository donationRepository;
    @RequestMapping("/")
    public String homeAction(Model model){
        List<Institution> allInstitutions = institutionRepository.findAll();
//        List<List<Institution>> pairs = new ArrayList<>();
//        for (int i = 0; i < allInstitutions.size(); i+=2) {
//            List<Institution> pair = new ArrayList<>();
//            for (int j = 0; j < 2; j++) {
//                if(i + j < allInstitutions.size()) {
//                    pair.add(allInstitutions.get(i + j));
//                }
//            }
//            pairs.add(pair);
//        }

        Long bagCount = donationRepository.sumQuantity();


        model.addAttribute("institutions", allInstitutions);
        model.addAttribute("bags",bagCount);
        model.addAttribute("donations", donationRepository.count());
        return "index";
    }
}
