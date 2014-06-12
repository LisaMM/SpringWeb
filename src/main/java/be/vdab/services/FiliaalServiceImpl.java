package be.vdab.services;

import be.vdab.dao.FiliaalDAO;
import be.vdab.entities.Filiaal;
import be.vdab.exceptions.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class FiliaalServiceImpl implements FiliaalService {
    private final FiliaalDAO filiaalDAO;
    private final JavaMailSender mailSender;
    private final Logger logger = 
        LoggerFactory.getLogger(FiliaalServiceImpl.class);

    @Autowired
    public FiliaalServiceImpl (FiliaalDAO filiaalDAO, 
        JavaMailSender mailSender) {
        this.filiaalDAO = filiaalDAO;
        this.mailSender = mailSender;
    }

    @Override
    @Transactional(readOnly = false)
    public void create(Filiaal filiaal) {
        if (filiaalDAO.findByNaam(filiaal.getNaam()) != null) {
                throw new FiliaalMetDezeNaamBestaatAlException();
        }
        filiaal.setId(filiaalDAO.save(filiaal).getId());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("kukuxumusu0@hotmail.com");
            helper.setSubject("Nieuw filiaal");
            helper.setText("Het filiaal <strong>" + filiaal.getNaam()
                + "</strong> werd toegevoegd", true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            logger.error("kan mail niet versturen", ex);
        }
    }

    @Override
    public Filiaal read(long id) {
            return filiaalDAO.findOne(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Filiaal filiaal) {
            Filiaal anderFiliaal = filiaalDAO.findByNaam(filiaal.getNaam());
            if (anderFiliaal != null && anderFiliaal.getId() != filiaal.getId()) {
                    throw new FiliaalMetDezeNaamBestaatAlException();
            }
            filiaalDAO.save(filiaal);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(long id) {
            Filiaal filiaal = filiaalDAO.findOne(id);
            if (! filiaal.getWerknemers().isEmpty()) {
                    throw new FiliaalHeeftNogWerknemersException();
            }
            filiaalDAO.delete(id);
    }

    @Override
    public Iterable<Filiaal> findAll() {
            return filiaalDAO.findAll(new Sort("naam"));
    }

    @Override
    @PreAuthorize("hasRole('manager')")
    public Iterable<Filiaal> findByPostcodeBetween(int van, int tot) {
            return filiaalDAO.findByAdresPostcodeBetweenOrderByNaamAsc(van, tot);
    }

    @Override
    public long findAantalFilialen() {
            return filiaalDAO.count();
    }
}
