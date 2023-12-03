package by.melnikov.medicinesxml.entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.YearMonth;
import java.util.Objects;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Сertificate", propOrder = {
        "permissionDate",
        "expiredDate"
})
public class MedicineCertificate {
    public static final String DEFAULT_REGISTRY_ORGANIZATION = "unknown organization";
    @XmlElement(name = "permission-date", required = true)
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth permissionDate;
    @XmlElement(name = "expired-date", required = true)
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth expiredDate;
    @XmlAttribute(name = "registration-number", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    private String id;
    @XmlAttribute(name = "registry-organization")
    private String registryOrganization;

    public MedicineCertificate() {
    }

    public MedicineCertificate(YearMonth permissionDate, YearMonth expiredDate, String id, String registryOrganization) {
        this.permissionDate = permissionDate;
        this.expiredDate = expiredDate;
        this.id = id;
        this.registryOrganization = registryOrganization;
    }

    public YearMonth getPermissionDate() {
        return permissionDate;
    }

    public void setPermissionDate(YearMonth permissionDate) {
        this.permissionDate = permissionDate;
    }

    public YearMonth getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(YearMonth expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistryOrganization() {
        return registryOrganization;
    }

    public void setRegistryOrganization(String registryOrganization) {
        this.registryOrganization = registryOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineCertificate that = (MedicineCertificate) o;

        if (!Objects.equals(permissionDate, that.permissionDate))
            return false;
        if (!Objects.equals(expiredDate, that.expiredDate)) return false;
        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(registryOrganization, that.registryOrganization);
    }

    @Override
    public int hashCode() {
        int result = permissionDate != null ? permissionDate.hashCode() : 0;
        result = 31 * result + (expiredDate != null ? expiredDate.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (registryOrganization != null ? registryOrganization.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "[", "]")
                .add("permissionDate=" + permissionDate)
                .add("expiredDate=" + expiredDate)
                .add("id='" + id + "'")
                .add("registryOrganization='" + registryOrganization + "'")
                .toString();
    }
}
