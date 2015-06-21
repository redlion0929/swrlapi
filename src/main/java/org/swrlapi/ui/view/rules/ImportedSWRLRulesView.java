package org.swrlapi.ui.view.rules;

import checkers.nullness.quals.NonNull;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.core.SWRLRuleRenderer;
import org.swrlapi.ui.model.SWRLRuleEngineModel;
import org.swrlapi.ui.view.SWRLAPIView;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Set;

public class ImportedSWRLRulesView extends JPanel implements SWRLAPIView
{
  private static final long serialVersionUID = 1L;

  @NonNull private final SWRLRuleEngineModel swrlRuleEngineModel;
  @NonNull private final SWRLRulesTableModel swrlRulesTableModel;

  public ImportedSWRLRulesView(@NonNull SWRLRuleEngineModel ruleEngineModel)
  {
    this.swrlRuleEngineModel = ruleEngineModel;
    this.swrlRulesTableModel = new SWRLRulesTableModel();
    JTable swrlRulesTable = new JTable(this.swrlRulesTableModel);

    JScrollPane scrollPane = new JScrollPane(swrlRulesTable);
    JViewport viewPort = scrollPane.getViewport();
    setLayout(new BorderLayout());
    viewPort.setBackground(swrlRulesTable.getBackground());
    add(BorderLayout.CENTER, scrollPane);
  }

  @Override
  public void validate()
  {
    this.swrlRulesTableModel.fireTableDataChanged();
    super.validate();
  }

  @Override
  public void update()
  {
    validate();
  }

  @NonNull private SWRLRuleEngine getSWRLRuleEngine()
  {
    return this.swrlRuleEngineModel.getSWRLRuleEngine();
  }

  @NonNull private SWRLRuleRenderer getSWRLRuleRenderer()
  {
    return this.swrlRuleEngineModel.getSWRLRuleRenderer();
  }

  private class SWRLRulesTableModel extends AbstractTableModel
  {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount()
    {
      return ImportedSWRLRulesView.this.getSWRLRuleEngine().getNumberOfImportedSWRLRules();
    }

    @Override
    public int getColumnCount()
    {
      return 1;
    }

    @NonNull @Override
    public Object getValueAt(int row, int column)
    {
      if (row < 0 || row >= getRowCount())
        return "OUT OF BOUNDS!";
      else {
        Set<SWRLAPIRule> swrlRules = ImportedSWRLRulesView.this.getSWRLRuleEngine().getSWRLRules();
        SWRLAPIRule[] swrlRuleArray = swrlRules.toArray(new SWRLAPIRule[swrlRules.size()]);
        SWRLAPIRule swrlRule = swrlRuleArray[row];
        return swrlRule.accept(ImportedSWRLRulesView.this.getSWRLRuleRenderer());
      }
    }
  }
}
