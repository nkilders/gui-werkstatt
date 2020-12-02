package de.noah.guiwerkstatt.property;

import de.noah.guiwerkstatt.gui.components.AComponent;
import de.noah.guiwerkstatt.property.properties.*;
import de.noah.guiwerkstatt.utility.MyEnumEntry;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyList {
    private final AComponent component;
    private List<AbstractProperty> properties;

    public PropertyList(AComponent component) {
        this.component = component;
        this.properties = new ArrayList<>();

        init();
    }

    private void init() {
        final Container container = component.getComponent();

        properties.add(new VarNameProperty(component));
        properties.add(new SizeProperty(component));
        properties.add(new CursorProperty(container));
        properties.add(new BackgroundProperty(container));
        properties.add(new ForegroundProperty(container));
        properties.add(new EnabledProperty(container));
        properties.add(new FocusableProperty(container));
        properties.add(new FontProperty(container));

        boolean isContentPane = false;
        if (component.getParent() != null) {
            if (component.getParent() instanceof AComponent) {
                if (((AComponent) component.getParent()).getComponent() instanceof JFrame) {
                    isContentPane = true;
                }
            }
        }

        if (container instanceof JFrame) {
            final JFrame frame = (JFrame) container;

            properties.add(new AbstractStringProperty<JFrame>("Title", frame, null) {

                @Override
                public String getValue() {
                    return component.getTitle();
                }

                @Override
                protected void setVal(String value) {
                    component.setTitle(value);
                }

                @Override
                public String getCodeLine() {
                    return String.format(".setTitle(\"%s\");", getValue().replace("\"", "\\\""));
                }
            });

            properties.add(new AbstractMyEnumProperty<JFrame, Integer>("Close Operation", frame, null, new MyEnumEntry[]{
                    new MyEnumEntry("Do nothing", 0),
                    new MyEnumEntry("Hide", 1),
                    new MyEnumEntry("Dispose", 2),
                    new MyEnumEntry("Exit", 3)
            }) {

                @Override
                public Integer getValue() {
                    return component.getDefaultCloseOperation();
                }

                @Override
                protected void setVal(Integer value) {
                    component.setDefaultCloseOperation(value);
                }

                @Override
                public String getCodeLine() {
                    return String.format(".setDefaultCloseOperation(%s);", getValue().toString());
                }
            });

            // TODO: Icon
        } else if (!isContentPane && container instanceof JComponent) {
            final JComponent jcomp = (JComponent) container;

            properties.add(new LocationProperty(container));
            properties.add(new TooltipTextProperty(jcomp));
            properties.add(new VisibleProperty(container));

            if (container instanceof JButton) {
                final JButton button = (JButton) container;

                properties.add(new AbstractStringProperty<JButton>("Text", button, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });

                properties.add(new AlignmentProperty(button, true));
                properties.add(new AlignmentProperty(button, false));
            } else if (container instanceof JCheckBox) {
                final JCheckBox checkBox = (JCheckBox) container;

                properties.add(new AbstractBooleanProperty<JCheckBox>("Selected", checkBox, null) {

                    @Override
                    public Boolean getValue() {
                        return component.isSelected();
                    }

                    @Override
                    protected void setVal(Boolean value) {
                        component.setSelected(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setSelected(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractStringProperty<JCheckBox>("Text", checkBox, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\"));
                    }
                });

                properties.add(new AlignmentProperty(checkBox, true));
                properties.add(new AlignmentProperty(checkBox, false));
            } else if (container instanceof JLabel) {
                final JLabel label = (JLabel) container;

                properties.add(new AbstractStringProperty<JLabel>("Text", label, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });

                properties.add(new AbstractMyEnumProperty<JLabel, Integer>("H Alignment", label, null, new MyEnumEntry[]{
                        new MyEnumEntry("Left", 2),
                        new MyEnumEntry("Center", 0),
                        new MyEnumEntry("Right", 4),
                        new MyEnumEntry("Leading", 10),
                        new MyEnumEntry("Trailing", 11)
                }) {

                    @Override
                    public Integer getValue() {
                        return component.getHorizontalAlignment();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setHorizontalAlignment(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setHorizontalAlignment(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractMyEnumProperty<JLabel, Integer>("V Alignment", label, null, new MyEnumEntry[]{
                        new MyEnumEntry("Top", 1),
                        new MyEnumEntry("Center", 0),
                        new MyEnumEntry("Bottom", 3)
                }) {

                    @Override
                    public Integer getValue() {
                        return component.getVerticalAlignment();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setVerticalAlignment(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setVerticalAlignment(%s);", getValue().toString());
                    }
                });
            } else if (container instanceof JPasswordField) {
                final JPasswordField pfield = (JPasswordField) container;

                properties.add(new AbstractStringProperty<JPasswordField>("Text", pfield, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });

                properties.add(new AbstractCharProperty<JPasswordField>("Echo Char", pfield, null) {

                    @Override
                    public Character getValue() {
                        return component.getEchoChar();
                    }

                    @Override
                    protected void setVal(Character value) {
                        component.setEchoChar(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setEchoChar('%s');", getValue().toString());
                    }
                });

                properties.add(new AbstractMyEnumProperty<JPasswordField, Integer>("H Alignment", pfield, null, new MyEnumEntry[]{
                        new MyEnumEntry("Left", 2),
                        new MyEnumEntry("Center", 0),
                        new MyEnumEntry("Right", 4),
                        new MyEnumEntry("Leading", 10),
                        new MyEnumEntry("Trailing", 11)
                }) {

                    @Override
                    public Integer getValue() {
                        return component.getHorizontalAlignment();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setHorizontalAlignment(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setHorizontalAlignment(%s);", getValue().toString());
                    }
                });

                properties.add(new EditableProperty(pfield));
            } else if (container instanceof JProgressBar) {
                final JProgressBar progBar = (JProgressBar) container;

                properties.add(new AbstractIntProperty<JProgressBar>("Value", progBar, null) {

                    @Override
                    public Integer getValue() {
                        return component.getValue();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setValue(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setValue(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JProgressBar>("Minimum", progBar, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMinimum();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMinimum(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMinimum(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JProgressBar>("Maximum", progBar, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMaximum();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMaximum(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMaximum(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractBooleanProperty<JProgressBar>("String painted", progBar, null) {

                    @Override
                    public Boolean getValue() {
                        return component.isStringPainted();
                    }

                    @Override
                    protected void setVal(Boolean value) {
                        component.setStringPainted(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setStringPainted(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractStringProperty<JProgressBar>("String", progBar, null) {

                    @Override
                    public String getValue() {
                        return component.getString();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setString(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setString(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });
            } else if (container instanceof JSeparator) {
                final JSeparator separator = (JSeparator) container;

                properties.add(new AbstractMyEnumProperty<JSeparator, Integer>("Orientation", separator, null, new MyEnumEntry[]{
                        new MyEnumEntry("Horizontal", 0),
                        new MyEnumEntry("Vertical", 1)
                }) {

                    @Override
                    public Integer getValue() {
                        return component.getOrientation();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setOrientation(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setOrientation(%s);", getValue().toString());
                    }
                });
            } else if (container instanceof JSlider) {
                final JSlider slider = (JSlider) container;

                properties.add(new AbstractIntProperty<JSlider>("Value", slider, null) {

                    @Override
                    public Integer getValue() {
                        return component.getValue();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setValue(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setValue(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JSlider>("Minimun", slider, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMinimum();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMinimum(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMinimum(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JSlider>("Maximum", slider, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMaximum();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMaximum(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMaximum(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JSlider>("Major tick space", slider, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMajorTickSpacing();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMajorTickSpacing(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMajorTickSpacing(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JSlider>("Minor tick space", slider, null) {

                    @Override
                    public Integer getValue() {
                        return component.getMinorTickSpacing();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setMinorTickSpacing(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setMinorTickSpacing(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractBooleanProperty<JSlider>("Paint ticks", slider, null) {

                    @Override
                    public Boolean getValue() {
                        return component.getPaintTicks();
                    }

                    @Override
                    protected void setVal(Boolean value) {
                        component.setPaintTicks(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setPaintTicks(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractBooleanProperty<JSlider>("Paint labels", slider, null) {

                    @Override
                    public Boolean getValue() {
                        return component.getPaintLabels();
                    }

                    @Override
                    protected void setVal(Boolean value) {
                        component.setPaintLabels(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setPaintLabels(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractBooleanProperty<JSlider>("Paint track", slider, null) {

                    @Override
                    public Boolean getValue() {
                        return component.getPaintTrack();
                    }

                    @Override
                    protected void setVal(Boolean value) {
                        component.setPaintTrack(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setPaintTrack(%s);", getValue().toString());
                    }
                });
            } else if (container instanceof JTextArea) {
                final JTextArea textArea = (JTextArea) container;

                properties.add(new AbstractStringProperty<JTextArea>("Text", textArea, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });

                properties.add(new AbstractIntProperty<JTextArea>("Rows", textArea, null) {

                    @Override
                    public Integer getValue() {
                        return component.getRows();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setRows(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setRows(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JTextArea>("Columns", textArea, null) {

                    @Override
                    public Integer getValue() {
                        return component.getColumns();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setColumns(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setColumns(%s);", getValue().toString());
                    }
                });

                properties.add(new AbstractIntProperty<JTextArea>("Tab Size", textArea, null) {

                    @Override
                    public Integer getValue() {
                        return component.getTabSize();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setTabSize(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setTabSize(%s);", getValue().toString());
                    }
                });

                properties.add(new EditableProperty(textArea));
            } else if(container instanceof JTextField) {
                final JTextField textField = (JTextField) container;

                properties.add(new AbstractStringProperty<JTextField>("Text", textField, null) {

                    @Override
                    public String getValue() {
                        return component.getText();
                    }

                    @Override
                    protected void setVal(String value) {
                        component.setText(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setText(\"%s\");", getValue().replace("\"", "\\\""));
                    }
                });

                properties.add(new EditableProperty(textField));

                properties.add(new AbstractMyEnumProperty<JTextField, Integer>("H Alignment", textField, null, new MyEnumEntry[] {
                        new MyEnumEntry("Left", 2),
                        new MyEnumEntry("Center", 0),
                        new MyEnumEntry("Right", 4),
                        new MyEnumEntry("Leading", 10),
                        new MyEnumEntry("Trailing", 11)
                }) {

                    @Override
                    public Integer getValue() {
                        return component.getHorizontalAlignment();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setHorizontalAlignment(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setHorizontalAlignment(%s);", getValue().toString());
                    }
                });



                properties.add(new AbstractIntProperty<JTextField>("Columns", textField, null) {

                    @Override
                    public Integer getValue() {
                        return component.getColumns();
                    }

                    @Override
                    protected void setVal(Integer value) {
                        component.setColumns(value);
                    }

                    @Override
                    public String getCodeLine() {
                        return String.format(".setColumns(%s);", getValue().toString());
                    }
                });
            }
        }
    }

    public AbstractProperty getPropertyByName(String name) {
        for (AbstractProperty p : properties) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null;
    }

    public List<AbstractProperty> getAllProperties() {
        return properties;
    }
}