package net.diaowen.common.plugs.mapper;

import org.apache.commons.lang.StringUtils;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author 向亚林
 * 2018/2/9 16:53
 */
public class ImprovedNamingStrategy implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convert(identifier);
	}
	@Override
	public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convert(identifier);
	}
	@Override
	public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convert(identifier);
	}
	@Override
	public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convert(identifier);
	}
	@Override
	public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
		return convert(identifier);
	}

	private Identifier convert(Identifier identifier) {
		if (identifier == null || StringUtils.isBlank(identifier.getText())) {
			return identifier;
		}
		String regex = "([a-z])([A-Z])";
		String replacement = "$1_$2";
		String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
		return Identifier.toIdentifier(newName);
	}
}
