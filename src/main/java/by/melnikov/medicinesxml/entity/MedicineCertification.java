package by.melnikov.medicinesxml.entity;

import java.time.YearMonth;
import java.util.Objects;
import java.util.StringJoiner;

public class MedicineCertification {
    public static final String DEFAULT_REGISTRY_ORGANIZATION = "unknown";
    private YearMonth permissionDate;
    private YearMonth expiredDate;
    private String id;
    private String registryOrganization;

    public MedicineCertification() {
    }

    public MedicineCertification(YearMonth permissionDate, YearMonth expiredDate, String id, String registryOrganization) {
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

        MedicineCertification that = (MedicineCertification) o;

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
